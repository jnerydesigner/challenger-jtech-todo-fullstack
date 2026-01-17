export const getInitials = (name: string, max: number = 2) => {
    if (!name) return ''

    return name
        .trim()
        .split(/\s+/)
        .filter(Boolean)
        .slice(0, max)
        .map(word => word[0].toUpperCase())
        .join('')
}